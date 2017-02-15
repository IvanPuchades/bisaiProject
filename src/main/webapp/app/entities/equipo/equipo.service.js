(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('Equipo', Equipo);

    Equipo.$inject = ['$resource', 'DateUtils'];

    function Equipo ($resource, DateUtils) {
        var resourceUrl =  'api/equipos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaCreacion = DateUtils.convertLocalDateFromServer(data.fechaCreacion);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaCreacion = DateUtils.convertLocalDateToServer(copy.fechaCreacion);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaCreacion = DateUtils.convertLocalDateToServer(copy.fechaCreacion);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
