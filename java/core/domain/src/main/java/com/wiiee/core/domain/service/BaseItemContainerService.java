package com.wiiee.core.domain.service;

import com.wiiee.core.platform.data.BaseData;
import com.wiiee.core.platform.data.BaseItemContainer;
import com.wiiee.core.platform.exception.CoreException;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.ParameterizedType;

/**
 * Created by billwang on 3/3/18
 */
public abstract class BaseItemContainerService<T2 extends BaseData<String>, T1 extends BaseItemContainer<T2>> extends BaseService<T1, String> {
    private Class<T1> type;

    public BaseItemContainerService(MongoRepository<T1, String> repository) {
        super(repository);
        this.type = (Class<T1>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public ServiceResult<T2> getItems(String containerId) {
        try {
            T1 container = get(containerId).data;
            return new ServiceResult<T2>(container.getItems());
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T2> getByItemId(String itemId) {
        try {
            T1 container = get(BaseItemContainer.getContainerId(itemId)).data;
            return new ServiceResult<>(container.getItem(itemId));
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T2> addItem(String containerId, T2 item) {
        try {
            T1 container = get(containerId).data;

            if (container == null) {
                container = type.newInstance();
                container.setId(containerId);
            }

            item.setId(container.addItem(item));

            update(container);

            return new ServiceResult<T2>(item);
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T2> updateItem(T2 item) {
        try {
            T1 container = get(BaseItemContainer.getContainerId(item.getId())).data;
            container.updateItem(item);
            update(container);
            return new ServiceResult<T2>(item);
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T2> deleteItem(String itemId) {
        try {
            T1 container = get(BaseItemContainer.getContainerId(itemId)).data;
            container.removeItem(itemId);
            update(container);
            return ServiceResult.SUCCESS;
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }
}

