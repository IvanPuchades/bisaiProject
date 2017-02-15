(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('ValoracionEquipo', ValoracionEquipo);

    ValoracionEquipo.$inject = ['$resource', 'DateUtils'];

    function ValoracionEquipo ($resource, DateUtils) {
        var resourceUrl =  'api/valoracion-equipos/:id';

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
