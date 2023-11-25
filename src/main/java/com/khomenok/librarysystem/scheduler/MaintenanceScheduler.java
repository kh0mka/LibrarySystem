package com.khomenok.librarysystem.scheduler;

import com.khomenok.librarysystem.interceptor.MaintenanceInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceScheduler {

    private final MaintenanceInterceptor maintenanceInterceptor;

    public MaintenanceScheduler(MaintenanceInterceptor maintenanceInterceptor) {
        this.maintenanceInterceptor = maintenanceInterceptor;
    }

    @Scheduled(cron = "0 3 9 * * THU") // Крон израз за неделя в 03:00
    public void activateMaintenance() {
        System.out.println("MAINTENANCE ON");
        maintenanceInterceptor.activateMaintenanceMode();
    }

    @Scheduled(cron = "0 5 9 * * THU") // Крон израз за неделя в 03:20
    public void deactivateMaintenance() {
        System.out.println("MAINTENANCE OFF");
        maintenanceInterceptor.deactivateMaintenanceMode();
    }

//    @Scheduled(cron = "*/10 * * * * *")
////    @Scheduled(fixedRate = 10_000)
//    public void cleanUp() {
//        maintenanceInterceptor.activateMaintenanceMode();
//        System.out.println("Trigger cleanup of activation links. " + LocalDateTime.now());
//
//    }
}
