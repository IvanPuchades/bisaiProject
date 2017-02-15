(function() {
    'use strict';
    angular
        .module('bisaiApp')
        .factory('Torneo', Torneo);

    Torneo.$inject = ['$resource'];

    function Torneo ($resource) {
        var resourceUrl =  'api/torneos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
