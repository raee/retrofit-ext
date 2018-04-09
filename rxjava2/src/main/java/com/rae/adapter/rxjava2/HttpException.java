/*
 * Copyright (c) 2018.
 */
package com.rae.adapter.rxjava2;

import retrofit2.Response;

/** @deprecated Use {@link retrofit2.HttpException}. */
@Deprecated
public final class HttpException extends retrofit2.HttpException {
  public HttpException(Response<?> response) {
    super(response);
  }
}
