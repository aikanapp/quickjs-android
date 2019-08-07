/*
 * Copyright 2019 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.quickjs.android;

import androidx.annotation.Nullable;

import java.lang.reflect.Type;

public abstract class TypeAdapter<T> {

  public abstract JSValue toJSValue(Context context, T value);

  public abstract T fromJSValue(Context context, JSValue value);

  public interface Factory {
    @Nullable
    TypeAdapter<?> create(Type type);
  }

  public interface Context {
    // TODO Throw exception if not found
    /**
     * Returns a TypeAdapter for the type.
     * Returns {@code null} if no TypeAdapter matched.
     */
    @Nullable
    <T> TypeAdapter<T> getAdapter(Type type);

    /**
     * Creates a JavaScript undefined.
     */
    JSUndefined createJSUndefined();

    /**
     * Creates a JavaScript null.
     */
    JSNull createJSNull();

    /**
     * Creates a JavaScript boolean.
     */
    JSBoolean createJSBoolean(boolean value);

    /**
     * Creates a JavaScript number.
     */
    JSNumber createJSNumber(int value);

    /**
     * Creates a JavaScript number.
     */
    JSNumber createJSNumber(double value);

    /**
     * Creates a JavaScript string.
     */
    JSString createJSString(String value);

    /**
     * Creates a JavaScript object.
     */
    JSObject createJSObject();

    /**
     * Creates a JavaScript array.
     */
    JSArray createJSArray();
  }
}
