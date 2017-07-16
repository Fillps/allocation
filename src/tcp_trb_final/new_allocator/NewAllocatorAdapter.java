package tcp_trb_final.new_allocator;

import tcp_trb_final.new_allocator.dominio.BANCO_DADOS;
import room_xml.AllocationType;

/**
 * Created by gabja on 15/07/2017.
 */
public class NewAllocatorAdapter {
    private BANCO_DADOS bancoDados;
    private AllocationType allocationType;

    public NewAllocatorAdapter(AllocationType allocationType){
        this.allocationType = allocationType;
    }

    public void MakeBancoDados() {

    }

    public void OrganizeData() {

    }

    public void Allocate() {

    }

    public AllocationType getAllocationType() {
        return allocationType;
    }
}
