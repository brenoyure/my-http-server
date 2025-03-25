package br.albatross.myhttpserver.request.converters;

public interface MyServerRequestConverter<I, O> {

    O convert(I inputType);

}
