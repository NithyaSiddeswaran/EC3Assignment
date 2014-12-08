package com.cloud.Assignment;

/*
 * Copyright 2010-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;


/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
 * account, and be signed up to use Amazon SQS. For more information on Amazon
 * SQS, see http://aws.amazon.com/sqs.
 * <p>
 * WANRNING:</b> To avoid accidental leakage of your credentials, DO NOT keep
 * the credentials file in your source directory.
 */
public class QueueSender {
	
	public static String URLMap="QueueURL.properties";
	
	private boolean isMessagePosted=false;

	public boolean sendToQueue(String queue, String message)
			throws Exception {
		Properties props=new Properties();
		AWSCredentials credentials = null;
		String myQueueUrl=null;
		System.out.println("Queue :"+queue+" Message :"+message);
		try {
			credentials = new ClasspathPropertiesFileCredentialsProvider(
					"AwsCredentials.properties").getCredentials();
			ClassLoader loader=Thread.currentThread().getContextClassLoader();
			
			props.load(loader.getResourceAsStream("/QueueURL.properties"));
		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. "
							+ "Please make sure that your credentials file is at the correct "
							+ "location (/Users/prabhus/.aws/credentials), and is in valid format.",
					e);
		}
		AmazonSQS sqs = new AmazonSQSClient(credentials);
		Region usWest2 = Region.getRegion(Regions.US_EAST_1);
		sqs.setRegion(usWest2);

		try {
			myQueueUrl = props.getProperty(queue);
			System.out.println("URL :"+myQueueUrl);
			System.out.println("Sending " + message);
			sqs.sendMessage(new SendMessageRequest(myQueueUrl, message));
			isMessagePosted=true;
		} catch (AmazonServiceException ase) {
		
			throw new AmazonServiceException("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon SQS, but was rejected with an error response for some reason.");
			
		} catch (AmazonClientException ace) {
			System.out.println("Error Message: " + ace.getMessage());
			throw new AmazonClientException("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with SQS, such as not "
							+ "being able to access the network.");
			
			
		}
		return isMessagePosted;
	}
}