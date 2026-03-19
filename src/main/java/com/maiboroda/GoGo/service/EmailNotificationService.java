package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.CarNotificationDto;

public interface EmailNotificationService {
    void sendCarNotification(CarNotificationDto notification);
}
