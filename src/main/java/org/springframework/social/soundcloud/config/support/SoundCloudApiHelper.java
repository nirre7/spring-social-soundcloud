package org.springframework.social.soundcloud.config.support;

/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.soundcloud.api.SoundCloud;

/**
 * Support class for JavaConfig and XML configuration support.
 * Creates an API binding instance for the current user's connection.
 * @author Michael Lavelle
 */
public class SoundCloudApiHelper implements ApiHelper<SoundCloud> {

	private final UsersConnectionRepository usersConnectionRepository;

	private final UserIdSource userIdSource;
	

	private SoundCloudApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
		this.usersConnectionRepository = usersConnectionRepository;
		this.userIdSource = userIdSource;		
	}

	public SoundCloud getApi() {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting API binding instance for SoundCloud");
		}
		
		Connection<SoundCloud> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).findPrimaryConnection(SoundCloud.class);
		if (logger.isDebugEnabled() && connection == null) {
			logger.debug("No current connection; Returning default SoundCloudTemplate instance.");
		}
		return connection != null ? connection.getApi() : null;
	}

	private final static Log logger = LogFactory.getLog(SoundCloudApiHelper.class);

}