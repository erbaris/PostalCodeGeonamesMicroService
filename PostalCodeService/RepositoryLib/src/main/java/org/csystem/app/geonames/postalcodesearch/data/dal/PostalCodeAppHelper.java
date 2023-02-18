package org.csystem.app.geonames.postalcodesearch.data.dal;

import org.csystem.app.geonames.postalcodesearch.data.entity.PostalCode;
import org.csystem.app.geonames.postalcodesearch.data.entity.PostalCodeInfo;
import org.csystem.app.geonames.postalcodesearch.data.entity.PostalCodeQueryInfo;
import org.csystem.app.geonames.postalcodesearch.data.repository.IPostalCodeInfoRepository;
import org.csystem.app.geonames.postalcodesearch.data.repository.IPostalCodeQueryInfoRepository;
import org.csystem.app.geonames.postalcodesearch.data.repository.IPostalCodeRepository;
import org.springframework.stereotype.Component;

@Component
public class PostalCodeAppHelper {
    private final IPostalCodeRepository m_postalCodeRepository;
    private final IPostalCodeInfoRepository m_postalCodeInfoRepository;
    private final IPostalCodeQueryInfoRepository m_postalCodeQueryInfoRepository;

    public PostalCodeAppHelper(IPostalCodeRepository postalCodeRepository, IPostalCodeInfoRepository postalCodeInfoRepository, IPostalCodeQueryInfoRepository postalCodeQueryInfoRepository) {
        m_postalCodeRepository = postalCodeRepository;
        m_postalCodeInfoRepository = postalCodeInfoRepository;
        m_postalCodeQueryInfoRepository = postalCodeQueryInfoRepository;
    }

    public void savePostalCodeInfo(PostalCodeInfo pi)
    {
        var pqi = new PostalCodeQueryInfo();
        pqi.postalCodeInfo = pi;
        pqi.queryValue = pqi.postalCodeInfo.queryCount;

        m_postalCodeInfoRepository.save(pi);
        m_postalCodeQueryInfoRepository.save(pqi);
    }
    public void updatePostalCodeInfo(String code)
    {
        m_postalCodeInfoRepository.updateQueryDateTimeAndQueryCount(code);
        var pqi = new PostalCodeQueryInfo();

        pqi.postalCodeInfo = m_postalCodeInfoRepository.findById(code).get();
        pqi.queryValue = pqi.postalCodeInfo.queryCount;

        m_postalCodeQueryInfoRepository.save(pqi);
    }

    public Iterable<PostalCode> findPostalCodesByCode(String code)
    {
        return m_postalCodeRepository.findByCode(code);
    }
    public boolean isPostalCodeInfoExistById(String code)
    {
        return m_postalCodeInfoRepository.existsById(code);
    }
    //...
}
