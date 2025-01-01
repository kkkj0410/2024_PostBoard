package com.example.project.converter

interface ProviderUserConverter<T,R> {

    fun converter(t : T) : R?
}