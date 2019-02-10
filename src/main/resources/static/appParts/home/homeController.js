(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce', 'jwtHelper'];
        function homeController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce, jwtHelper) {
        	var hc=this;
            $scope.token = $stateParams.token;
            $scope.params = [];
            $scope.pretragaReci = "";
            $scope.searchResult = [];
            $scope.paramsResult = [];
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	else{
            		$http({
                        method: 'GET',
                        url: 'https://localhost:8087/NaucnaCentrala/login/checkValidity/'+$stateParams.token
                      }).then(function successCallback(response){
                    	  if(response.data==false)
                      		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
                      },
                        function errorCallback(response){
                            alert("Greska u zahtevu");
                        });
            	}
            }
            $scope.dodajParametar = function(){
                $scope.params.push({"operacija": "",
                	"polje": "",
                	"vrednost": "",
                	"fraza": false});
            }
            
            $scope.pretrazi = function(){
            	var p = $scope.params;
            	if(p=="" || p==undefined)
            		return;
            	var parametri = [];
            	for(var i=0; i<p.length; i++){
            		if(p[i].operacija=="" || p[i].operacija==undefined
            				|| p[i].polje=="" || p[i].polje==undefined
            				|| p[i].vrednost=="" || p[i].vrednost==undefined){
            			alert("Svi parametri pretrage moraju biti uneseni");
            			return;
            		}
            		var p0={"operacija": p[i].operacija,
                        	"polje": p[i].polje,
                        	"vrednost": p[i].vrednost,
                        	"fraza": p[i].fraza}
            		parametri.push(p0);
            	}
            	var data = {"parametri": parametri}
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/rest/search',
                    data: data
                  }).then(function successCallback(response){
                	  $scope.searchResult = namestiRezultat(response.data);
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu za pretragu");
                    });
            }
            $scope.obrisiParametar = function(parametar){
            	if($scope.params.length>=parametar)
            		$scope.params.splice(parametar, 1);
            }
            
            $scope.pretraziPoRecima = function(){
            	var data = $scope.pretragaReci;
            	if(data=="")
            		return;
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/rest/search/'+data
                  }).then(function successCallback(response){
                	  $scope.paramsResult = [];
                	  $scope.searchResult = namestiRezultat(response.data);
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu za pretragu");
                    });
            }
            var convertAutori = function(str){
            	var res = str.split(";");
            	var retVal = [];
            	for(var i=0; i<res.length; i++){
            		var podaci = res.split(",");
            		var ime = podaci[0].split(" ")[0];
            		var prezime = podaci[0].split(" ")[0];
            		var email = podaci[1];
            		retVal.push({
            			"ime": ime,
            			"prezime": prezime,
            			"email": email
            		});
            	}
            }
            var namestiRezultat = function(data){
            	var result = [];
            	var obj = {};
            	for (var i=0; i<data.length; i++){
					var res = data[i];
            		if(res.highlights.casopis==null)
            			obj.casopis=res.rad.casopis;
            		else
            			obj.casopis=res.highlights.casopis;
            		
            		if(res.highlights.naslov==null)
            			obj.naslov=res.rad.naslov;
            		else
            			obj.naslov=res.highlights.naslov;
            		
            		if(res.highlights.kljucniPojmovi==null)
            			obj.kljucniPojmovi=res.rad.kljucniPojmovi;
            		else
            			obj.kljucniPojmovi=res.highlights.kljucniPojmovi;
            		
            		if(res.highlights.tekstRada==null)
            			obj.tekstRada=res.rad.tekstRada;
            		else
            			obj.tekstRada=res.highlights.tekstRada;
            		
            		if(res.highlights.naucnaOblast==null)
            			obj.naucnaOblast=res.rad.naucnaOblast;
            		else
            			obj.naucnaOblast=res.highlights.naucnaOblast;
            		
            		if(res.highlights.autoriRada==null)
                		obj.autoriRada = convertAutori(res.rad.autoriRada);
            		else
            			obj.autoriRada = convertAutori(res.highlights.naucnaOblast);
            		result.push(obj);
            		obj = {};
            	}
            	return result;
            }
            $scope.zadaci = function(){
            	var token = $window.localStorage.getItem('token');
            	$location.path('/zadaci/'+token);
            }
            
            $scope.obrisiSve = function(){
            	$scope.searchResult = [];
                $scope.params = [];
                $scope.pretragaReci = "";
            }
            $scope.pronadjiSlicne = function(result){
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/rest/moreLikeThis/'+result
                  }).then(function successCallback(response){
                	  $scope.searchResult = response.data;
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu za slične radove");
                    });
            }
            $scope.trustDangerousSnippet = function(tekstRada){
            	return $sce.trustAsHtml(tekstRada);
            }
        }
})();