(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ClasificacionController', ClasificacionController);

    ClasificacionController.$inject = ['$scope', '$state', 'DataUtils', 'Clasificacion'];

    function ClasificacionController ($scope, $state, DataUtils, Clasificacion) {
        var vm = this;

        vm.clasificacions = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Clasificacion.query(function(result) {
                vm.clasificacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
