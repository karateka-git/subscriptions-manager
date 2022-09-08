package com.vlatrof.subscriptionsmanager.data.utils

import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity as EntitySubscription
import com.vlatrof.subscriptionsmanager.data.models.Subscription as DataSubscription
import com.vlatrof.subscriptionsmanager.domain.models.Subscription as DomainSubscription

object SubscriptionModelListMapper {

    fun mapEntityToData(entitySubscriptionList: List<EntitySubscription>): List<DataSubscription> {

        val dataSubscriptionList = mutableListOf<DataSubscription>()

        entitySubscriptionList.forEach {
            dataSubscriptionList.add(SubscriptionModelMapper.mapEntityToData(it))
        }

        return dataSubscriptionList

    }

    fun mapDataToEntity(dataSubscriptionList: List<DataSubscription>): List<EntitySubscription> {

        val entitySubscriptionList = mutableListOf<EntitySubscription>()

        dataSubscriptionList.forEach {
            entitySubscriptionList.add(SubscriptionModelMapper.mapDataToEntity(it))
        }

        return entitySubscriptionList

    }

    fun mapDataToDomain(dataSubscriptionList: List<DataSubscription>): List<DomainSubscription> {

        val domainSubscriptionList = mutableListOf<DomainSubscription>()

        dataSubscriptionList.forEach {
            domainSubscriptionList.add(SubscriptionModelMapper.mapDataToDomain(it))
        }

        return domainSubscriptionList

    }

    fun mapDomainToData(domainSubscriptionList: List<DomainSubscription>): List<DataSubscription> {

        val dataSubscriptionList = mutableListOf<DataSubscription>()

        domainSubscriptionList.forEach {
            dataSubscriptionList.add(SubscriptionModelMapper.mapDomainToData(it))
        }

        return dataSubscriptionList

    }

}