module core {
    requires com.google.gson;
    requires allentity;
    requires spi;
    requires dao;
    uses spi.ResponseDecoder;
    exports core;
}