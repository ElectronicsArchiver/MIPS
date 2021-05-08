/*
 *    Copyright 2021 ParkerTenBroeck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.parker.mips.plugin.exceptions;

/**
 * Thrown when attempting to load an invalid Plugin file
 */
public class InvalidPluginException extends Exception {
    private static final long serialVersionUID = -8242141640709409544L;

    /**
     * Constructs a new InvalidPluginException based on the given Exception
     *
     * @param cause Exception that triggered this Exception
     */
    public InvalidPluginException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new InvalidPluginException
     */
    public InvalidPluginException() {

    }

    /**
     * Constructs a new InvalidPluginException with the specified detail
     * message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *     by the getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the
     *     getCause() method). (A null value is permitted, and indicates that
     *     the cause is nonexistent or unknown.)
     */
    public InvalidPluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidPluginException with the specified detail
     * message
     *
     * @param message TThe detail message is saved for later retrieval by the
     *     getMessage() method.
     */
    public InvalidPluginException(final String message) {
        super(message);
    }
}