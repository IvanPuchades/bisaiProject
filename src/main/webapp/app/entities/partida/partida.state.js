(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('partida', {
            parent: 'entity',
            url: '/partida',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.partida.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/partida/partidas.html',
                    controller: 'PartidaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('partida');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('partida-detail', {
            parent: 'entity',
            url: '/partida/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.partida.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/partida/partida-detail.html',
                    controller: 'PartidaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('partida');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Partida', function($stateParams, Partida) {
                    return Partida.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'partida',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('partida-detail.edit', {
            parent: 'partida-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partida/partida-dialog.html',
                    controller: 'PartidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Partida', function(Partida) {
                            return Partida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('partida.new', {
            parent: 'partida',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partida/partida-dialog.html',
                    controller: 'PartidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fechaInicio: null,
                                fechaFinal: null,
                                resultadoEquipo1: null,
                                resultadoEquipo2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('partida', null, { reload: 'partida' });
                }, function() {
                    $state.go('partida');
                });
            }]
        })
        .state('partida.edit', {
            parent: 'partida',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partida/partida-dialog.html',
                    controller: 'PartidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Partida', function(Partida) {
                            return Partida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('partida', null, { reload: 'partida' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('partida.delete', {
            parent: 'partida',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partida/partida-delete-dialog.html',
                    controller: 'PartidaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Partida', function(Partida) {
                            return Partida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('partida', null, { reload: 'partida' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
