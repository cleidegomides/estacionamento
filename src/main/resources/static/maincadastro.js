var getJson = function(url, funcSucesso, funcErro){
    var httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", url, true);
    httpRequest.responseType = "json";

    httpRequest.addEventListener("readystatechange", 
        function(){
            if(httpRequest.readyState == 4){
                if(httpRequest.status == 200){
                    if(funcSucesso){
                        funcSucesso(httpRequest.response);
                    }
                } else {
                    if(funcErro){
                        funcErro(httpRequest.status, httpRequest.statusText);
                    }
                }
            }
        }
    );
}

var getMarcas = function(){
    var urlMarcal = "localhost:8080/estacionamento/dados/marcas?tipoVeiculo=moto";

    getJson(urlMarcal, 
        function(dados){
            Marcas = dados;
        },
        function(status, statusText){

        }
    );
}