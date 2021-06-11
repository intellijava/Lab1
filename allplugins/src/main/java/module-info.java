import responsetype.*;

module response.plugins {
    requires com.google.gson;
    requires spi;
    requires allentity;
    requires dao;
    uses GETFileResponse;
    uses GETJsonResponse;
    exports responsetype;
    provides spi.ResponseDecoder with GETFileResponse,
            GETJsonResponse,
            GETTextResponse,
            HEADJsonResponse,
            HEADTextResponse,
            HEADFileResponse,
            POSTJsonResponse,
            POSTTextResponse;
}