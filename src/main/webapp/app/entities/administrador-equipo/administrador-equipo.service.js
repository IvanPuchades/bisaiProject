(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('AdministradorEquipo', AdministradorEquipo);

    AdministradorEquipo.$inject = ['$resource', 'DateUtils'];

    function AdministradorEquipo ($resource, DateUtils) {
        var resourceUrl =  'api/administrador-equipos/:id';

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
