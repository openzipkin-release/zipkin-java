/**
 * Copyright 2015 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.zipkin;

import java.io.IOException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class CodecTest {

  protected abstract Codec codec();

  @Test
  public void spanRoundTrip() throws IOException {
    for (Span span : TestObjects.TRACE) {
      byte[] bytes = codec().writeSpan(span);
      assertThat(codec().readSpan(bytes))
          .isEqualTo(span);
    }
  }

  @Test
  public void spanDecodesToNullOnEmpty() throws IOException {
    assertThat(codec().readSpan(new byte[0]))
        .isNull();
  }

  @Test
  public void dependencyLinkRoundTrip() throws IOException {
    DependencyLink link = DependencyLink.create("tfe", "mobileweb", 6);
    byte[] bytes = codec().writeDependencyLink(link);
    assertThat(codec().readDependencyLink(bytes))
        .isEqualTo(link);
  }

  @Test
  public void dependencyLinkDecodesToNullOnEmpty() throws IOException {
    assertThat(codec().readDependencyLink(new byte[0]))
        .isNull();
  }
}
