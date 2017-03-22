(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('Torneo', Torneo);

    Torneo.$inject = ['$resource', 'DateUtils'];

    function Torneo ($resource, DateUtils) {
        var resourceUrl =  'api/torneos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaInicio = DateUtils.convertDateTimeFromServer(data.fechaInicio);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
