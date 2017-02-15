(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('ValoracionJugador', ValoracionJugador);

    ValoracionJugador.$inject = ['$resource', 'DateUtils'];

    function ValoracionJugador ($resource, DateUtils) {
        var resourceUrl =  'api/valoracion-jugadors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.hora = DateUtils.convertDateTimeFromServer(data.hora);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
