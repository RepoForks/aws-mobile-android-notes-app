package com.amazonaws.mobile.samples.mynotes;

/*
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
 * except in compliance with the License. A copy of the License is located at
 *
 *    http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the License.
 */
import android.content.Context;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;

public class AWSMobileClient {
    private static AWSMobileClient instance = null;
    private Context context;
    protected AWSConfiguration awsConfig;
    protected IdentityManager identityManager;
    protected PinpointManager pinpointManager;

    public static AWSMobileClient getInstance() {
        return instance;
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new AWSMobileClient(context);
        }
    }

    private AWSMobileClient(Context context) {
        this.context = context;
        this.awsConfig = new AWSConfiguration(context);

        identityManager = new IdentityManager(context, awsConfig);
        IdentityManager.setDefaultIdentityManager(identityManager);

        final AWSCredentialsProvider cp = identityManager.getCredentialsProvider();
        PinpointConfiguration config = new PinpointConfiguration(context, cp, awsConfig);
        pinpointManager = new PinpointManager(config);
    }

    public static AWSConfiguration getConfiguration() {
        return instance.awsConfig;
    }

    public static IdentityManager getIdentityManager() {
        return instance.identityManager;
    }

    public static PinpointManager getPinpointManager() {
        return instance.pinpointManager;
    }

    /*
     * Static methods to submit Pinpoint Events
     */
    public static AnalyticsClient startSession() {
        instance.pinpointManager.getSessionClient().startSession();
        return instance.pinpointManager.getAnalyticsClient();
    }

    public static AnalyticsClient stopSession() {
        instance.pinpointManager.getSessionClient().stopSession();
        return instance.pinpointManager.getAnalyticsClient();
    }

    public static AnalyticsClient getAnalyticsClient() {
        return instance.pinpointManager.getAnalyticsClient();
    }
}
