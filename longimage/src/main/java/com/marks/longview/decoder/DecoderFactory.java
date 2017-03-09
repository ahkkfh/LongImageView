package com.marks.longview.decoder;

/**
 * Interface for decoder (and region decoder) factories.
 * @param <T> the class of decoder that will be produced.
 *  解码工厂接口，提供产生解码器的方法
 */
public interface DecoderFactory<T> {

  /**
   * 生产类型为{@link T}的解码器
   * @return 解码器实例
   */
  T make() throws IllegalAccessException, InstantiationException;
}
