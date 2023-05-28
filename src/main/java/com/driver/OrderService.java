package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    OrderRepository or = new OrderRepository();

    public void addOrder(Order order) {
        or.addOrder(order);
    }

    public void addPartner(String partnerId) {
        or.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        or.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return or.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return or.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return or.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return or.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return or.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return or.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return or.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return or.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        or.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        or.deleteOrderById(orderId);
    }
}
