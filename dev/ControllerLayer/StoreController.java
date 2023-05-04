package ControllerLayer;

import DomainLayer.Store;

public interface StoreController {
    Store findStoreById(int storeId);
}
