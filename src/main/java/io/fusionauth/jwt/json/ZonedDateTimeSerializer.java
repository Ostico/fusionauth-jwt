/*
 * Copyright (c) 2016, FusionAuth, All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package io.fusionauth.jwt.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * Jackson serializer for the ZonedDateTime class. Serialize to Seconds since Epoch.
 *
 * @author Daniel DeGroff
 */
public class ZonedDateTimeSerializer extends StdScalarSerializer<ZonedDateTime> {
  public ZonedDateTimeSerializer() {
    super(ZonedDateTime.class);
  }

  @Override
  public void serialize(ZonedDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value == null) {
      jgen.writeNull();
    } else {
      jgen.writeNumber(value.toEpochSecond());
    }
  }
}
