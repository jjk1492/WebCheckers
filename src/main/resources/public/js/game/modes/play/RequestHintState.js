/**
 * This module exports the RequestHintState class constructor.
 *
 * This component is an concrete implementation of a state
 * for the Game view; this state represents the requesting hint state.
 * Its job is to find possible moves and then highlight the squares
 * so that the player can choose a move if they're stuck.
 */

define(function(require){
    'use strict';

    // imports
    const PlayModeConstants = require('./PlayModeConstants');
    const AjaxUtils = require('../../util/AjaxUtils');

    /**
     * Constructor function.
     *
     * @param {PlayController} controller
     *    The Play mode controller object.
     */

    function RequestHintState(controller){
        // Prviate attributes
        this._controller = controller;
    }

    // Public methods

    /**
     * Get the name of this state.
     */
    RequestHintState.prototype.getName = function getName() {
        return PlayModeConstants.REQUEST_HINT;
    }

    RequestHintState.prototype.onEntry = function onEntry() {
        this._controller.disableButton(PlayModeConstants.HINT_BUTTON_ID);
        this._controller.disableButton(PlayModeConstants.BACKUP_BUTTON_ID);
        this._controller.disableButton(PlayModeConstants.SUBMIT_BUTTON_ID);
        this._controller.disableButton(PlayModeConstants.RESIGN_BUTTON_ID);
        AjaxUtils.callServer(
            // the action takes no data
            '/requestHint', '',
            // the handler method should be run in the context of 'this' State object
            handleResponse, this);
    }

    //
    // Private methods
    //

    function handleResponse(message) {
        this._controller.displayMessage(message);
        var isTurnActive = this._controller.isTurnActive();
        this._controller.setState(isTurnActive ? PlayModeConstants.STABLE_TURN : PlayModeConstants.EMPTY_TURN);
    }

    return RequestHintState;
});