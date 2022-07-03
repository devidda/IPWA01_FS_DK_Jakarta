package de.iu.jakarta_jsf.services;

import de.iu.jakarta_jsf.persistence.MainCo2DataDAO;
import de.iu.jakarta_jsf.persistence.MainCo2DataEntity;
import de.iu.jakarta_jsf.persistence.RequestedValueEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class DataServiceImpl implements DataService{

    @Inject
    MainCo2DataDAO mainDataDAO;

    @Override
    public void updateMainDataWithRequest(RequestedValueEntity valueEntity) {
        mainDataDAO.saveOrUpdate(valueEntity);
    }

    @Override
    public List<MainCo2DataEntity> getLatestData() {
        return mainDataDAO.getAllLatestData();
    }

    @Override
    public List<MainCo2DataEntity> getMainDataByCountry(String countryLabel) {
        return mainDataDAO.getMainDataByCountry(countryLabel);
    }
}
