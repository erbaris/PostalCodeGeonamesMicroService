package org.csystem.app.geonames.postalcodesearch.data.repository;

import com.karandev.util.data.repository.ICrudRepository;
import org.csystem.app.geonames.postalcodesearch.data.entity.PostalCodeQueryInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostalCodeQueryInfoRepository extends CrudRepository<PostalCodeQueryInfo, Long> {

    /*
    @Modifying
    @Query("insert into PostalCodeQuesryInfo (postal_code_query_info_id, query_date_time, query_value, code) values (:id, :queryDateTime, :queryValue, :postalCodeInfo.code) ")
    int addPostalQueryInfo(@Param("code") String code);


     */

}
