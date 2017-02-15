(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('JuegoController', JuegoController);

    JuegoController.$inject = ['$scope', '$state', 'DataUtils', 'Juego'];

    function JuegoController ($scope, $state, DataUtils, Juego) {
        var vm = this;

        vm.juegos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Juego.query(function(result) {
                vm.juegos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
