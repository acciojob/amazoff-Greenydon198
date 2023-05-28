package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orders;
    HashMap<String,DeliveryPartner> partners;
    HashMap<String, ArrayList<String>> pair;//partnerid:{orderids}
    
    HashMap<String,String> order_partner;

    public OrderRepository(){
        orders = new HashMap<>();
        partners = new HashMap<>();
        pair = new HashMap<>();
        order_partner = new HashMap<>();
    }

    public void addOrder(Order order) {
        orders.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        partners.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(orders.containsKey(orderId) && partners.containsKey(partnerId)) {
            ArrayList<String> curr = pair.getOrDefault(partnerId, new ArrayList<>());
            curr.add(orderId);
            pair.put(partnerId, curr);
            partners.get(partnerId).setNumberOfOrders(curr.size());
            order_partner.put(orderId,partnerId);
        }
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partners.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        if(pair.containsKey(partnerId))
            return pair.get(partnerId).size();
        else
            return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        if(pair.containsKey(partnerId))
            return pair.get(partnerId);
        else
            return new ArrayList<>();
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orders.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
//        int assigned = 0;
//        for(String parnter:pair.keySet()){
//            assigned += pair.get(parnter).size();
//        }

        return orders.size() - order_partner.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        if(!pair.containsKey(partnerId) || pair.get(partnerId).size()==0)
            return null;
        int left = 0;
        int Time = Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3));
        for(String o:pair.get(partnerId)){
            if(orders.get(o).getDeliveryTime()>Time)
                left++;
        }
        return left;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        if(!pair.containsKey(partnerId))return "00:00";
        int last = 0;
        for(String o:pair.get(partnerId)){
            if(orders.get(o).getDeliveryTime()>last)
                last = orders.get(o).getDeliveryTime();
        }
        return to24hr(last);
    }

    public String to24hr(int time){
        String hr = (time/60)+"";
        String min = time%60 +"";
        if(min.length()==1)
            min = "0"+min;
        if(hr.length()==1)
            hr = "0"+hr;

        return hr+":"+min;
    }

    public void deletePartnerById(String partnerId) {
        if(!pair.containsKey(partnerId))return;
        for(String o:pair.get(partnerId)){
            if(order_partner.containsKey(o))
                order_partner.remove(o);
        }
        pair.remove(partnerId);
        if(partners.containsKey(partnerId))
            partners.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        if(order_partner.containsKey(orderId)){
            String partnerid = order_partner.get(orderId);
            ArrayList<String> curr = pair.get(partnerid);
            for(int i=curr.size()-1;i>=0;i--){
                if(orderId.equals(curr.get(i))){
                    curr.remove(i);
                    break;
                }
            }

            partners.get(partnerid).setNumberOfOrders(pair.get(partnerid).size());

            order_partner.remove(orderId);
        }
        if(orders.containsKey(orderId))
            orders.remove(orderId);
    }
}
