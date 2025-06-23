package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Mapper}.${entity}Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
* <p>
    * ${table.comment} Service 类（无接口）
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
public class ${entity}Service {

    @Resource
    private ${entity}Mapper ${entity?uncap_first}Mapper;

    public BigInteger create${entity}(String images, String ${entity?uncap_first}Name) {
        ${entity} ${entity?uncap_first} = new ${entity}();
        ${entity?uncap_first}.set${entity}Images(images);
        ${entity?uncap_first}.set${entity}Name(${entity?uncap_first}Name);
        ${entity?uncap_first}.setIsDeleted(0);

        ${entity?uncap_first}Mapper.insert(${entity?uncap_first});
        return ${entity?uncap_first}.getId();
    }

    public BigInteger update${entity}(BigInteger ${entity?uncap_first}Id, String images, String ${entity?uncap_first}Name) {
        ${entity} ${entity?uncap_first} = new ${entity}();
        ${entity?uncap_first}.set${entity}Images(images);
        ${entity?uncap_first}.set${entity}Name(${entity?uncap_first}Name);

        if (${entity?uncap_first}Mapper.get${entity}NameById(${entity?uncap_first}Id) == null) {
            throw new RuntimeException("书籍不存在");
        }
        ${entity?uncap_first}.setId(${entity?uncap_first}Id);
        ${entity?uncap_first}Mapper.update(${entity?uncap_first});
        return ${entity?uncap_first}.getId();
    }

    public int delete(BigInteger id) {
        if (id == null) {
            return 0;
        }
            return ${entity?uncap_first}Mapper.delete(id);
    }

    public String get${entity}NameById(BigInteger id){
        return ${entity?uncap_first}Mapper.get${entity}NameById(id);
    }

    public List<${entity}> getAll${entity}(){
        return ${entity?uncap_first}Mapper.getAll${entity}();
    }

    public List<${entity}> getByOffset(Integer offset, Integer limit){
        return ${entity?uncap_first}Mapper.getByOffset(offset, limit);
    }

}
