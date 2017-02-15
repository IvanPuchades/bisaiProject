(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('valoracion-jugador', {
            parent: 'entity',
            url: '/valoracion-jugador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.valoracionJugador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoracion-jugador/valoracion-jugadors.html',
                    controller: 'ValoracionJugadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoracionJugador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('valoracion-jugador-detail', {
            parent: 'entity',
            url: '/valoracion-jugador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.valoracionJugador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoracion-jugador/valoracion-jugador-detail.html',
                    controller: 'ValoracionJugadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoracionJugador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ValoracionJugador', function($stateParams, ValoracionJugador) {
                    return ValoracionJugador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'valoracion-jugador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('valoracion-jugador-detail.edit', {
            parent: 'valoracion-jugador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-jugador/valoracion-jugador-dialog.html',
                    controller: 'ValoracionJugadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValoracionJugador', function(ValoracionJugador) {
                            return ValoracionJugador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoracion-jugador.new', {
            parent: 'valoracion-jugador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-jugador/valoracion-jugador-dialog.html',
                    controller: 'ValoracionJugadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hora: null,
                                meGusta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('valoracion-jugador', null, { reload: 'valoracion-jugador' });
                }, function() {
                    $state.go('valoracion-jugador');
                });
            }]
        })
        .state('valoracion-jugador.edit', {
            parent: 'valoracion-jugador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-jugador/valoracion-jugador-dialog.html',
                    controller: 'ValoracionJugadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValoracionJugador', function(ValoracionJugador) {
                            return ValoracionJugador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoracion-jugador', null, { reload: 'valoracion-jugador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoracion-jugador.delete', {
            parent: 'valoracion-jugador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-jugador/valoracion-jugador-delete-dialog.html',
                    controller: 'ValoracionJugadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ValoracionJugador', function(ValoracionJugador) {
                            return ValoracionJugador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoracion-jugador', null, { reload: 'valoracion-jugador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
