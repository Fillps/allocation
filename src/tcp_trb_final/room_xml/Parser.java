package tcp_trb_final.room_xml;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by filip on 20/05/2017.
 */
public class Parser {

    public static AllocationType importFile(String path) throws JAXBException, XMLStreamException, IOException {
        if (path.endsWith(".xml"))
            return unmarshall(path);
        else if (path.endsWith(".xlsx"))
            return importExcel(path);
        return null;
    }

    private static AllocationType unmarshall(String path) throws JAXBException, XMLStreamException {
        JAXBContext jaxbContext = JAXBContext.newInstance(AllocationType.class);

        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(path));

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return  (AllocationType) jaxbUnmarshaller.unmarshal(xsr);
    }

    public static void marshall(AllocationType allocationType, String path) throws JAXBException {
        File file = new File(path);

        JAXBContext jaxbContext = JAXBContext.newInstance(AllocationType.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(allocationType, file);
    }

    private static AllocationType importExcel(String path) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(path));
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
        excelFile.close();
        Sheet demandasSheet = workbook.getSheet("Demandas");
        Sheet featuresSheet = workbook.getSheet("Features");
        Sheet espacoFisicoSheet = workbook.getSheet("Espaço Físico");

        AllocationType allocationType = new AllocationType();
        allocationType.setCourses(parseDemandas(demandasSheet));
        allocationType.setFeatures(parseFeatures(featuresSheet));
        allocationType.setBuildings(parseEspacoFisico(espacoFisicoSheet));
        return allocationType;
    }


    private static String[] iterateCells(int length, Iterator<Cell> cellIterator){
        String[] answers = new String[length];
        for (int i = 0 ; i < answers.length ; i++){
            answers[i] = null;
            if (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellTypeEnum().equals(CellType.STRING))
                    answers[cell.getColumnIndex()] = cell.getStringCellValue();
                else if (cell.getCellTypeEnum().equals(CellType.NUMERIC))
                    answers[cell.getColumnIndex()] = String.valueOf(cell.getNumericCellValue()).replace(".0","");
                else if (cell.getCellTypeEnum().equals(CellType.BOOLEAN))
                    answers[cell.getColumnIndex()] = String.valueOf(cell.getBooleanCellValue());
                else
                    answers[cell.getColumnIndex()] = null;
            }
        }
        return answers;
    }
    /**
             name 0
             id 1
             number_of_students 2
             teacher 3
             id2 4
             room_id 5
             duration 6
             building_id 7
             weekday 8
             start_time 9
             requires_building_id 10
             requires_room_id 11
             feature_ids 12
             */
    private static CoursesType parseDemandas(Sheet sheet){
        List<CourseType> courseTypeList = new ArrayList<>();
        List<GroupType> groupTypeList = new ArrayList<>();
        List<SessionType> sessionTypeList = new ArrayList<>();
        CourseType currentCourse = new CourseType();
        GroupType currentGroup = new GroupType();

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();

            String[] answers = iterateCells(13,cellIterator);
            if (answers[1]==null)
                break;

            if (currentCourse.getId() == null && currentGroup.getId() == null){
                currentCourse.setId(answers[1]);
                currentCourse.setName(answers[0]);

                currentGroup.setId(answers[4]);
                currentGroup.setNumberOfStudents(answers[2]);
                currentGroup.setTeacher(answers[3]);
            }
            else{
                if (!currentGroup.getId().equals(answers[4]) || !currentCourse.getName().equals(answers[0])){
                    currentGroup.setSession(sessionTypeList);
                    groupTypeList.add(currentGroup);
                    sessionTypeList = new ArrayList<>();
                    currentGroup = new GroupType();

                    currentGroup.setId(answers[4]);
                    currentGroup.setNumberOfStudents(answers[2]);
                    currentGroup.setTeacher(answers[3]);

                }
                if (!currentCourse.getName().equals(answers[0])){
                    currentCourse.setGroup(groupTypeList);
                    courseTypeList.add(currentCourse);

                    groupTypeList = new ArrayList<>();
                    currentCourse = new CourseType();

                    currentCourse.setId(answers[1]);
                    currentCourse.setName(answers[0]);
                }
            }
            SessionType sessionType = new SessionType();
            sessionType.setRoomId(answers[5]);
            sessionType.setDuration(answers[6]);
            sessionType.setBuildingId(answers[7]);
            sessionType.setWeekday(answers[8]);
            sessionType.setStartTime(answers[9]);
            sessionType.setRequiresBuildingId(answers[10]);
            sessionType.setRequiresRoomId(answers[11]);
            sessionType.setFeatureIds(answers[12]);

            sessionTypeList.add(sessionType);
        }
        currentGroup.setSession(sessionTypeList);
        groupTypeList.add(currentGroup);
        currentCourse.setGroup(groupTypeList);
        courseTypeList.add(currentCourse);

        CoursesType coursesType = new CoursesType();
        coursesType.setCourse(courseTypeList);
        return coursesType;
    }

/**
 *  name 0
 *  id4 1
 *  hidden 2
 */
    private static FeaturesType parseFeatures(Sheet sheet) {
        List<FeatureType> featureTypeList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            String[] answers = iterateCells(3 , cellIterator);

            if (answers[1]==null)
                break;

            FeatureType featureType = new FeatureType();
            featureType.setName(answers[0]);
            featureType.setId(answers[1]);
            featureType.setHidden(answers[2]);

            featureTypeList.add(featureType);
        }
        FeaturesType featuresType = new FeaturesType();
        featuresType.setFeature(featureTypeList);
        return featuresType;
    }

    /**
     * building 0
     * room_label 1
     * feature_ids 2
     * number_of_places 3
     * available_for_allocation 4
     * note 5
     * @param sheet
     * @return
     */
    private static BuildingsType parseEspacoFisico(Sheet sheet) {
        List<BuildingType> buildingTypeList = new ArrayList<>();
        List<RoomType> roomTypeList = new ArrayList<>();
        BuildingType currentBuilding = new BuildingType();

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            String[] answers = iterateCells(6,cellIterator);

            if (answers[1]==null)
                break;

            if (currentBuilding.getId()==null)
                currentBuilding.setId(answers[0]);
            else if (!currentBuilding.getId().equals(answers[0])){
                currentBuilding.setRoom(roomTypeList);
                buildingTypeList.add(currentBuilding);
                roomTypeList = new ArrayList<>();
                currentBuilding = new BuildingType();
                currentBuilding.setId(answers[0]);
            }

            RoomType roomType = new RoomType();
            roomType.setId(answers[1]);
            roomType.setFeatureIds(answers[2]);
            roomType.setNumberOfPlaces(answers[3]);
            roomType.setAvailableForAllocation(answers[4]);
            roomType.setNote(answers[5]);
            roomTypeList.add(roomType);
        }
        currentBuilding.setRoom(roomTypeList);
        buildingTypeList.add(currentBuilding);

        BuildingsType buildingsType = new BuildingsType();
        buildingsType.setBuilding(buildingTypeList);
        return buildingsType;
    }
}
