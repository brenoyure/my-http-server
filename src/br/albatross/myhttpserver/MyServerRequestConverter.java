package br.albatross.myhttpserver;

public interface MyServerRequestConverter<I, O> {

    O convert(I inputType);

}
