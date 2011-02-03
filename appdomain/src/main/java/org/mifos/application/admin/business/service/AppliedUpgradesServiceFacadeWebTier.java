package org.mifos.application.admin.business.service;

import org.mifos.application.admin.servicefacade.AppliedUpgradesServiceFacade;
import org.mifos.db.upgrade.ChangeSetInfo;
import org.mifos.db.upgrade.DatabaseUpgradeSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AppliedUpgradesServiceFacadeWebTier implements AppliedUpgradesServiceFacade {

    private DatabaseUpgradeSupport databaseUpgradeSupport;

    @Autowired
    public AppliedUpgradesServiceFacadeWebTier(DatabaseUpgradeSupport databaseUpgradeSupport){
        this.databaseUpgradeSupport = databaseUpgradeSupport;
    }

    @Override
    public List<ChangeSetInfo> getAppliedUpgrades() {
        return  databaseUpgradeSupport.listRanUpgrades();
    }
}