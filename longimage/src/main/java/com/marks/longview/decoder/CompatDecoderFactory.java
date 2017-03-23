package com.marks.longview.decoder;


/**
 * Compatibility factory to instantiate decoders with empty public constructors.
 * @param <T> The base type of the decoder this factory will produce.
 *            兼容性解码器实现解码器接口，通过make()生产解码器
 */
public class CompatDecoderFactory<T> implements DecoderFactory<T> {

    private Class<? extends T> clazz;

    public CompatDecoderFactory(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T make() throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
