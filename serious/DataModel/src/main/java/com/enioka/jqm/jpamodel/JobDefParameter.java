/**
 * Copyright © 2013 enioka. All rights reserved
 * Authors: Pierre COPPEE (pierre.coppee@enioka.com)
 * Contributors : Marc-Antoine GOUILLART (marc-antoine.gouillart@enioka.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enioka.jqm.jpamodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JobDefParameter")
public class JobDefParameter implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5308516206913425230L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, length=50, name="KEYNAME")
	private String key;
	@Column(nullable=false, length=1000)
	private String value;

	public JobParameter jDPtoJP(final JobDefParameter jdp) {

		final JobParameter jp = new JobParameter();

		jp.setKey(jdp.getKey());
		jp.setValue(jdp.getValue());

		return jp;
	}


	public String getKey() {

		return key;
	}

	public void setKey(final String key) {

		this.key = key;
	}

	public String getValue() {

		return value;
	}

	public void setValue(final String value) {

		this.value = value;
	}

}
