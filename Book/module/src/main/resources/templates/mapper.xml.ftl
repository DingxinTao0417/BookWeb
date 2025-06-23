<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <!-- 插入 -->
    <insert id="insert" parameterType="${package.Entity}.${table.entityName}">
        insert into ${table.name} (
        <#list table.fields as field>
            <#if !field.keyFlag && field.columnName != "id" && field.columnName != "is_deleted">
                <if test="category.${field.propertyName} != null and category.${field.propertyName} != ''">
                    ${field.columnName},
                </if>
            </#if>
            <#if !field.keyFlag && field.columnName == "is_deleted">
                ${field.columnName}
            </#if>
        </#list>
        ) values (
        <#list table.fields as field>
            <#if !field.keyFlag && field.columnName != "id" && field.propertyName != "isDeleted">
                <if test="category.${field.propertyName} != null and category.${field.propertyName} != ''">
                    <#noparse>#{category.</#noparse>${field.propertyName}<#noparse>}</#noparse>,
                </if>
            </#if>
            <#if !field.keyFlag && field.propertyName == "isDeleted">
                <#noparse>#{category.</#noparse>${field.propertyName}<#noparse>}</#noparse>
            </#if>
        </#list>
        )
    </insert>

    <!--更新-->
    <update id="update" parameterType="${package.Entity}.${table.entityName}">
        update ${table.name}
        <set>
            <#if table.fields?filter(f -> f.columnName == "id")?size != 0>
                id = <#noparse>#{category.id}</#noparse>,
            </#if>

            <#list table.fields as field>
                <#if !field.keyFlag && field.columnName != "id" && field.columnName != "is_deleted">
                    <if test="category.${field.propertyName} != null and category.${field.propertyName} != ''">
                        ${field.columnName} = <#noparse>#{category.</#noparse>${field.propertyName}<#noparse>}</#noparse>,
                    </if>
                </#if>
            </#list>
        </set>
        where id = <#noparse>#{category.id}</#noparse>
    </update>

    <select id="getByOffset" resultType="${package.Entity}.${table.entityName}">
        select * from ${table.name}
        where is_deleted = 0
        order by id desc
        limit <#noparse>#{limit}</#noparse> offset <#noparse>#{offset}</#noparse>
    </select>

</mapper>
