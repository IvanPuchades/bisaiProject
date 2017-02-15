(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('Partida', Partida);

    Partida.$inject = ['$resource', 'DateUtils'];

    function Partida ($resource, DateUtils) {
        var resourceUrl =  'api/partidas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaInicio = DateUtils.convertDateTimeFromServer(data.fechaInicio);
                        data.fechaFinal = DateUtils.convertDateTimeFromServer(data.fechaFinal);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
