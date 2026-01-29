/**
 * Toast : notification en bas à droite
 * showToast(message, type) avec type: 'success' | 'error' | 'info'
 *
 * Modal de confirmation : remplace confirm()
 * showConfirm({ title, message }).then(ok => { ... })
 */

(function () {
    'use strict';

    function getContainer(id) {
        let el = document.getElementById(id);
        if (!el) {
            el = document.createElement('div');
            el.id = id;
            document.body.appendChild(el);
        }
        return el;
    }

    function showToast(message, type) {
        type = type || 'info';
        var container = getContainer('toast-container');
        var toast = document.createElement('div');
        toast.className = 'toast toast--' + type;
        toast.setAttribute('role', 'alert');
        toast.textContent = message;
        container.appendChild(toast);
        requestAnimationFrame(function () {
            toast.classList.add('toast--visible');
        });
        var duration = 4000;
        var timer = setTimeout(function () {
            toast.classList.remove('toast--visible');
            setTimeout(function () {
                if (toast.parentNode) toast.parentNode.removeChild(toast);
            }, 300);
        }, duration);
        toast.addEventListener('click', function () {
            clearTimeout(timer);
            toast.classList.remove('toast--visible');
            setTimeout(function () {
                if (toast.parentNode) toast.parentNode.removeChild(toast);
            }, 300);
        });
    }

    function showConfirm(options) {
        var title = (options && options.title) || 'Confirmer';
        var message = (options && options.message) || 'Êtes-vous sûr ?';
        var confirmLabel = (options && options.confirmLabel) || 'Confirmer';
        var cancelLabel = (options && options.cancelLabel) || 'Annuler';
        var confirmClass = (options && options.danger) ? 'btn-danger' : 'btn-primary';

        var overlay = getContainer('confirm-modal');
        overlay.innerHTML =
            '<div class="confirm-modal__backdrop" id="confirm-backdrop"></div>' +
            '<div class="confirm-modal__dialog" role="dialog" aria-modal="true" aria-labelledby="confirm-title">' +
            '  <h2 class="confirm-modal__title" id="confirm-title">' + escapeHtml(title) + '</h2>' +
            '  <p class="confirm-modal__message">' + escapeHtml(message) + '</p>' +
            '  <div class="confirm-modal__actions">' +
            '    <button type="button" class="btn btn-secondary" id="confirm-cancel">' + escapeHtml(cancelLabel) + '</button>' +
            '    <button type="button" class="btn ' + confirmClass + '" id="confirm-ok">' + escapeHtml(confirmLabel) + '</button>' +
            '  </div>' +
            '</div>';
        overlay.classList.add('confirm-modal--open');

        var resolvePromise;
        var promise = new Promise(function (resolve) {
            resolvePromise = resolve;
        });

        function onKey(e) {
            if (e.key === 'Escape') {
                close(false);
            }
        }

        function close(result) {
            overlay.classList.remove('confirm-modal--open');
            document.removeEventListener('keydown', onKey);
            resolvePromise(result);
        }

        document.getElementById('confirm-backdrop').addEventListener('click', function () { close(false); });
        document.getElementById('confirm-cancel').addEventListener('click', function () { close(false); });
        document.getElementById('confirm-ok').addEventListener('click', function () { close(true); });
        document.addEventListener('keydown', onKey);

        return promise;
    }

    function escapeHtml(text) {
        var div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    window.showToast = showToast;
    window.showConfirm = showConfirm;
})();
