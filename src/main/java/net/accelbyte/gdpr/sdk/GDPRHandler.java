/*
 * Copyright (c) 2023 AccelByte Inc. All Rights Reserved
 * This program is made available under the terms of the MIT License.
 */

package net.accelbyte.gdpr.sdk;

import net.accelbyte.gdpr.sdk.object.DataGenerationResult;

public interface GDPRHandler {

    /**
     * Process data generation for the requested user.
     *
     * The concrete implementation should put the data result into "data" property inside DataGenerationResult object.
     * The "data" property (inside DataGenerationResult object) was in form of Map{ModuleId, byte[]},
     * this allows the concrete implementation to categorized multiple data based on the modules they have.
     * Example:
     * <pre>{@code
     *  Map<ModuleId, byte[]> = {
     *    entitlement: entitlementData,
     *    wallet: walletData,
     *    transaction: transactionData
     *  }
     *  }</pre>
     *
     * @param namespace             namespace of user
     * @param userId                user id
     * @param isPublisherNamespace  indicate whether the "namespace" is a publisher namespace or game namespace
     * @return DataGenerationResult object
     */
    DataGenerationResult ProcessDataGeneration(String namespace, String userId, boolean isPublisherNamespace);

    /**
     * Process data deletion for the requested user.
     *
     * @param namespace             namespace of user
     * @param userId                user id
     * @param isPublisherNamespace  indicate whether the "namespace" is a publisher namespace or game namespace
     */
    void ProcessDataDeletion(String namespace, String userId, boolean isPublisherNamespace);

    /**
     * Process data restriction for the requested user.
     *
     * This process will ensure the personal data associated with disabled account
     * ceased to be available to other users.
     *
     * @param namespace             namespace of user
     * @param userId                user id
     * @param restrict              restrict or not
     * @param isPublisherNamespace  indicate whether the "namespace" is a publisher namespace or game namespace
     */
    void ProcessDataRestriction(String namespace, String userId, boolean restrict, boolean isPublisherNamespace);
}
