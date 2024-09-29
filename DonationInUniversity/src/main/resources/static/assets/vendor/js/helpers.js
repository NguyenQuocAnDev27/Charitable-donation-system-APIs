/*
 * ATTENTION: The "eval" devtool has been used (maybe by default in mode: "development").
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
(function webpackUniversalModuleDefinition(root, factory) {
    if (typeof exports === 'object' && typeof module === 'object')
        module.exports = factory();
    else if (typeof define === 'function' && define.amd)
        define([], factory);
    else {
        var a = factory();
        for (var i in a) (typeof exports === 'object' ? exports : root)[i] = a[i];
    }
})(self, function () {
    return /******/ (function () { // webpackBootstrap
        /******/
        "use strict";
        /******/
        var __webpack_modules__ = ({

            /***/ "./js/helpers.js":
            /*!***********************!*\
              !*** ./js/helpers.js ***!
              \***********************/
            /***/ (function (__unused_webpack_module, __webpack_exports__, __webpack_require__) {

                eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   Helpers: function() { return /* binding */ Helpers; }\n/* harmony export */ });\nfunction _toArray(r) { return _arrayWithHoles(r) || _iterableToArray(r) || _unsupportedIterableToArray(r) || _nonIterableRest(); }\nfunction _iterableToArray(r) { if (\"undefined\" != typeof Symbol && null != r[Symbol.iterator] || null != r[\"@@iterator\"]) return Array.from(r); }\nfunction _slicedToArray(r, e) { return _arrayWithHoles(r) || _iterableToArrayLimit(r, e) || _unsupportedIterableToArray(r, e) || _nonIterableRest(); }\nfunction _nonIterableRest() { throw new TypeError(\"Invalid attempt to destructure non-iterable instance.\\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.\"); }\nfunction _unsupportedIterableToArray(r, a) { if (r) { if (\"string\" == typeof r) return _arrayLikeToArray(r, a); var t = {}.toString.call(r).slice(8, -1); return \"Object\" === t && r.constructor && (t = r.constructor.name), \"Map\" === t || \"Set\" === t ? Array.from(r) : \"Arguments\" === t || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t) ? _arrayLikeToArray(r, a) : void 0; } }\nfunction _arrayLikeToArray(r, a) { (null == a || a > r.length) && (a = r.length); for (var e = 0, n = Array(a); e < a; e++) n[e] = r[e]; return n; }\nfunction _iterableToArrayLimit(r, l) { var t = null == r ? null : \"undefined\" != typeof Symbol && r[Symbol.iterator] || r[\"@@iterator\"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }\nfunction _arrayWithHoles(r) { if (Array.isArray(r)) return r; }\n// Constants\nvar TRANS_EVENTS = ['transitionend', 'webkitTransitionEnd', 'oTransitionEnd'];\nvar TRANS_PROPERTIES = ['transition', 'MozTransition', 'webkitTransition', 'WebkitTransition', 'OTransition'];\nvar INLINE_STYLES = \"\\n.layout-menu-fixed .layout-navbar-full .layout-menu,\\n.layout-page {\\n  padding-top: {navbarHeight}px !important;\\n}\\n.content-wrapper {\\n  padding-bottom: {footerHeight}px !important;\\n}\";\n\n// Guard\nfunction requiredParam(name) {\n  throw new Error(\"Parameter required\".concat(name ? \": `\".concat(name, \"`\") : ''));\n}\nvar Helpers = {\n  // Root Element\n  ROOT_EL: typeof window !== 'undefined' ? document.documentElement : null,\n  // Large screens breakpoint\n  LAYOUT_BREAKPOINT: 1200,\n  // Resize delay in milliseconds\n  RESIZE_DELAY: 200,\n  menuPsScroll: null,\n  mainMenu: null,\n  // Internal variables\n  _curStyle: null,\n  _styleEl: null,\n  _resizeTimeout: null,\n  _resizeCallback: null,\n  _transitionCallback: null,\n  _transitionCallbackTimeout: null,\n  _listeners: [],\n  _initialized: false,\n  _autoUpdate: false,\n  _lastWindowHeight: 0,\n  // *******************************************************************************\n  // * Utilities\n  // ---\n  // Scroll To Active Menu Item\n  _scrollToActive: function _scrollToActive() {\n    var animate = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;\n    var duration = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 500;\n    var layoutMenu = this.getLayoutMenu();\n    if (!layoutMenu) return;\n    var activeEl = layoutMenu.querySelector('li.menu-item.active:not(.open)');\n    if (activeEl) {\n      // t = current time\n      // b = start value\n      // c = change in value\n      // d = duration\n      var easeInOutQuad = function easeInOutQuad(t, b, c, d) {\n        t /= d / 2;\n        if (t < 1) return c / 2 * t * t + b;\n        t -= 1;\n        return -c / 2 * (t * (t - 2) - 1) + b;\n      };\n      var element = this.getLayoutMenu().querySelector('.menu-inner');\n      if (typeof activeEl === 'string') {\n        activeEl = document.querySelector(activeEl);\n      }\n      if (typeof activeEl !== 'number') {\n        activeEl = activeEl.getBoundingClientRect().top + element.scrollTop;\n      }\n\n      // If active element's top position is less than 2/3 (66%) of menu height than do not scroll\n      if (activeEl < parseInt(element.clientHeight * 2 / 3, 10)) return;\n      var start = element.scrollTop;\n      var change = activeEl - start - parseInt(element.clientHeight / 2, 10);\n      var startDate = +new Date();\n      if (animate === true) {\n        var animateScroll = function animateScroll() {\n          var currentDate = +new Date();\n          var currentTime = currentDate - startDate;\n          var val = easeInOutQuad(currentTime, start, change, duration);\n          element.scrollTop = val;\n          if (currentTime < duration) {\n            requestAnimationFrame(animateScroll);\n          } else {\n            element.scrollTop = change;\n          }\n        };\n        animateScroll();\n      } else {\n        element.scrollTop = change;\n      }\n    }\n  },\n  // ---\n  // Add classes\n  _addClass: function _addClass(cls) {\n    var el = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : this.ROOT_EL;\n    if (el && el.length !== undefined) {\n      // Add classes to multiple elements\n      el.forEach(function (e) {\n        if (e) {\n          cls.split(' ').forEach(function (c) {\n            return e.classList.add(c);\n          });\n        }\n      });\n    } else if (el) {\n      // Add classes to single element\n      cls.split(' ').forEach(function (c) {\n        return el.classList.add(c);\n      });\n    }\n  },\n  // ---\n  // Remove classes\n  _removeClass: function _removeClass(cls) {\n    var el = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : this.ROOT_EL;\n    if (el && el.length !== undefined) {\n      // Remove classes to multiple elements\n      el.forEach(function (e) {\n        if (e) {\n          cls.split(' ').forEach(function (c) {\n            return e.classList.remove(c);\n          });\n        }\n      });\n    } else if (el) {\n      // Remove classes to single element\n      cls.split(' ').forEach(function (c) {\n        return el.classList.remove(c);\n      });\n    }\n  },\n  // Toggle classes\n  _toggleClass: function _toggleClass() {\n    var el = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : this.ROOT_EL;\n    var cls1 = arguments.length > 1 ? arguments[1] : undefined;\n    var cls2 = arguments.length > 2 ? arguments[2] : undefined;\n    if (el.classList.contains(cls1)) {\n      el.classList.replace(cls1, cls2);\n    } else {\n      el.classList.replace(cls2, cls1);\n    }\n  },\n  // ---\n  // Has class\n  _hasClass: function _hasClass(cls) {\n    var el = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : this.ROOT_EL;\n    var result = false;\n    cls.split(' ').forEach(function (c) {\n      if (el.classList.contains(c)) result = true;\n    });\n    return result;\n  },\n  _findParent: function _findParent(el, cls) {\n    if (el && el.tagName.toUpperCase() === 'BODY' || el.tagName.toUpperCase() === 'HTML') return null;\n    el = el.parentNode;\n    while (el && el.tagName.toUpperCase() !== 'BODY' && !el.classList.contains(cls)) {\n      el = el.parentNode;\n    }\n    el = el && el.tagName.toUpperCase() !== 'BODY' ? el : null;\n    return el;\n  },\n  // ---\n  // Trigger window event\n  _triggerWindowEvent: function _triggerWindowEvent(name) {\n    if (typeof window === 'undefined') return;\n    if (document.createEvent) {\n      var event;\n      if (typeof Event === 'function') {\n        event = new Event(name);\n      } else {\n        event = document.createEvent('Event');\n        event.initEvent(name, false, true);\n      }\n      window.dispatchEvent(event);\n    } else {\n      window.fireEvent(\"on\".concat(name), document.createEventObject());\n    }\n  },\n  // ---\n  // Trigger event\n  _triggerEvent: function _triggerEvent(name) {\n    this._triggerWindowEvent(\"layout\".concat(name));\n    this._listeners.filter(function (listener) {\n      return listener.event === name;\n    }).forEach(function (listener) {\n      return listener.callback.call(null);\n    });\n  },\n  // ---\n  // Update style\n  _updateInlineStyle: function _updateInlineStyle() {\n    var navbarHeight = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 0;\n    var footerHeight = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 0;\n    if (!this._styleEl) {\n      this._styleEl = document.createElement('style');\n      this._styleEl.type = 'text/css';\n      document.head.appendChild(this._styleEl);\n    }\n    var newStyle = INLINE_STYLES.replace(/\\{navbarHeight\\}/gi, navbarHeight).replace(/\\{footerHeight\\}/gi, footerHeight);\n    if (this._curStyle !== newStyle) {\n      this._curStyle = newStyle;\n      this._styleEl.textContent = newStyle;\n    }\n  },\n  // ---\n  // Remove style\n  _removeInlineStyle: function _removeInlineStyle() {\n    if (this._styleEl) document.head.removeChild(this._styleEl);\n    this._styleEl = null;\n    this._curStyle = null;\n  },\n  // ---\n  // Redraw layout menu (Safari bugfix)\n  _redrawLayoutMenu: function _redrawLayoutMenu() {\n    var layoutMenu = this.getLayoutMenu();\n    if (layoutMenu && layoutMenu.querySelector('.menu')) {\n      var inner = layoutMenu.querySelector('.menu-inner');\n      var scrollTop = inner.scrollTop;\n      var pageScrollTop = document.documentElement.scrollTop;\n      layoutMenu.style.display = 'none';\n      // layoutMenu.offsetHeight\n      layoutMenu.style.display = '';\n      inner.scrollTop = scrollTop;\n      document.documentElement.scrollTop = pageScrollTop;\n      return true;\n    }\n    return false;\n  },\n  // ---\n  // Check for transition support\n  _supportsTransitionEnd: function _supportsTransitionEnd() {\n    if (window.QUnit) return false;\n    var el = document.body || document.documentElement;\n    if (!el) return false;\n    var result = false;\n    TRANS_PROPERTIES.forEach(function (evnt) {\n      if (typeof el.style[evnt] !== 'undefined') result = true;\n    });\n    return result;\n  },\n  // ---\n  // Calculate current navbar height\n  _getNavbarHeight: function _getNavbarHeight() {\n    var _this = this;\n    var layoutNavbar = this.getLayoutNavbar();\n    if (!layoutNavbar) return 0;\n    if (!this.isSmallScreen()) return layoutNavbar.getBoundingClientRect().height;\n\n    // Needs some logic to get navbar height on small screens\n\n    var clonedEl = layoutNavbar.cloneNode(true);\n    clonedEl.id = null;\n    clonedEl.style.visibility = 'hidden';\n    clonedEl.style.position = 'absolute';\n    Array.prototype.slice.call(clonedEl.querySelectorAll('.collapse.show')).forEach(function (el) {\n      return _this._removeClass('show', el);\n    });\n    layoutNavbar.parentNode.insertBefore(clonedEl, layoutNavbar);\n    var navbarHeight = clonedEl.getBoundingClientRect().height;\n    clonedEl.parentNode.removeChild(clonedEl);\n    return navbarHeight;\n  },\n  // ---\n  // Get current footer height\n  _getFooterHeight: function _getFooterHeight() {\n    var layoutFooter = this.getLayoutFooter();\n    if (!layoutFooter) return 0;\n    return layoutFooter.getBoundingClientRect().height;\n  },\n  // ---\n  // Get animation duration of element\n  _getAnimationDuration: function _getAnimationDuration(el) {\n    var duration = window.getComputedStyle(el).transitionDuration;\n    return parseFloat(duration) * (duration.indexOf('ms') !== -1 ? 1 : 1000);\n  },\n  // ---\n  // Set menu hover state\n  _setMenuHoverState: function _setMenuHoverState(hovered) {\n    this[hovered ? '_addClass' : '_removeClass']('layout-menu-hover');\n  },\n  // ---\n  // Toggle collapsed\n  _setCollapsed: function _setCollapsed(collapsed) {\n    var _this2 = this;\n    if (this.isSmallScreen()) {\n      if (collapsed) {\n        this._removeClass('layout-menu-expanded');\n      } else {\n        setTimeout(function () {\n          _this2._addClass('layout-menu-expanded');\n        }, this._redrawLayoutMenu() ? 5 : 0);\n      }\n    }\n  },\n  // ---\n  // Add layout sivenav toggle animationEnd event\n  _bindLayoutAnimationEndEvent: function _bindLayoutAnimationEndEvent(modifier, cb) {\n    var _this3 = this;\n    var menu = this.getMenu();\n    var duration = menu ? this._getAnimationDuration(menu) + 50 : 0;\n    if (!duration) {\n      modifier.call(this);\n      cb.call(this);\n      return;\n    }\n    this._transitionCallback = function (e) {\n      if (e.target !== menu) return;\n      _this3._unbindLayoutAnimationEndEvent();\n      cb.call(_this3);\n    };\n    TRANS_EVENTS.forEach(function (e) {\n      menu.addEventListener(e, _this3._transitionCallback, false);\n    });\n    modifier.call(this);\n    this._transitionCallbackTimeout = setTimeout(function () {\n      _this3._transitionCallback.call(_this3, {\n        target: menu\n      });\n    }, duration);\n  },\n  // ---\n  // Remove layout sivenav toggle animationEnd event\n  _unbindLayoutAnimationEndEvent: function _unbindLayoutAnimationEndEvent() {\n    var _this4 = this;\n    var menu = this.getMenu();\n    if (this._transitionCallbackTimeout) {\n      clearTimeout(this._transitionCallbackTimeout);\n      this._transitionCallbackTimeout = null;\n    }\n    if (menu && this._transitionCallback) {\n      TRANS_EVENTS.forEach(function (e) {\n        menu.removeEventListener(e, _this4._transitionCallback, false);\n      });\n    }\n    if (this._transitionCallback) {\n      this._transitionCallback = null;\n    }\n  },\n  // ---\n  // Bind delayed window resize event\n  _bindWindowResizeEvent: function _bindWindowResizeEvent() {\n    var _this5 = this;\n    this._unbindWindowResizeEvent();\n    var cb = function cb() {\n      if (_this5._resizeTimeout) {\n        clearTimeout(_this5._resizeTimeout);\n        _this5._resizeTimeout = null;\n      }\n      _this5._triggerEvent('resize');\n    };\n    this._resizeCallback = function () {\n      if (_this5._resizeTimeout) clearTimeout(_this5._resizeTimeout);\n      _this5._resizeTimeout = setTimeout(cb, _this5.RESIZE_DELAY);\n    };\n    window.addEventListener('resize', this._resizeCallback, false);\n  },\n  // ---\n  // Unbind delayed window resize event\n  _unbindWindowResizeEvent: function _unbindWindowResizeEvent() {\n    if (this._resizeTimeout) {\n      clearTimeout(this._resizeTimeout);\n      this._resizeTimeout = null;\n    }\n    if (this._resizeCallback) {\n      window.removeEventListener('resize', this._resizeCallback, false);\n      this._resizeCallback = null;\n    }\n  },\n  _bindMenuMouseEvents: function _bindMenuMouseEvents() {\n    var _this6 = this;\n    if (this._menuMouseEnter && this._menuMouseLeave && this._windowTouchStart) return;\n    var layoutMenu = this.getLayoutMenu();\n    if (!layoutMenu) return this._unbindMenuMouseEvents();\n    if (!this._menuMouseEnter) {\n      this._menuMouseEnter = function () {\n        if (_this6.isSmallScreen() || _this6._hasClass('layout-transitioning')) {\n          return _this6._setMenuHoverState(false);\n        }\n        return _this6._setMenuHoverState(false);\n      };\n      layoutMenu.addEventListener('mouseenter', this._menuMouseEnter, false);\n      layoutMenu.addEventListener('touchstart', this._menuMouseEnter, false);\n    }\n    if (!this._menuMouseLeave) {\n      this._menuMouseLeave = function () {\n        _this6._setMenuHoverState(false);\n      };\n      layoutMenu.addEventListener('mouseleave', this._menuMouseLeave, false);\n    }\n    if (!this._windowTouchStart) {\n      this._windowTouchStart = function (e) {\n        if (!e || !e.target || !_this6._findParent(e.target, '.layout-menu')) {\n          _this6._setMenuHoverState(false);\n        }\n      };\n      window.addEventListener('touchstart', this._windowTouchStart, true);\n    }\n  },\n  _unbindMenuMouseEvents: function _unbindMenuMouseEvents() {\n    if (!this._menuMouseEnter && !this._menuMouseLeave && !this._windowTouchStart) return;\n    var layoutMenu = this.getLayoutMenu();\n    if (this._menuMouseEnter) {\n      if (layoutMenu) {\n        layoutMenu.removeEventListener('mouseenter', this._menuMouseEnter, false);\n        layoutMenu.removeEventListener('touchstart', this._menuMouseEnter, false);\n      }\n      this._menuMouseEnter = null;\n    }\n    if (this._menuMouseLeave) {\n      if (layoutMenu) {\n        layoutMenu.removeEventListener('mouseleave', this._menuMouseLeave, false);\n      }\n      this._menuMouseLeave = null;\n    }\n    if (this._windowTouchStart) {\n      if (layoutMenu) {\n        window.addEventListener('touchstart', this._windowTouchStart, true);\n      }\n      this._windowTouchStart = null;\n    }\n    this._setMenuHoverState(false);\n  },\n  // *******************************************************************************\n  // * Methods\n  scrollToActive: function scrollToActive() {\n    var animate = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;\n    this._scrollToActive(animate);\n  },\n  // ---\n  // Collapse / expand layout\n  setCollapsed: function setCollapsed() {\n    var _this7 = this;\n    var collapsed = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : requiredParam('collapsed');\n    var animate = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : true;\n    var layoutMenu = this.getLayoutMenu();\n    if (!layoutMenu) return;\n    this._unbindLayoutAnimationEndEvent();\n    if (animate && this._supportsTransitionEnd()) {\n      this._addClass('layout-transitioning');\n      if (collapsed) this._setMenuHoverState(false);\n      this._bindLayoutAnimationEndEvent(function () {\n        // Collapse / Expand\n        if (_this7.isSmallScreen) _this7._setCollapsed(collapsed);\n      }, function () {\n        _this7._removeClass('layout-transitioning');\n        _this7._triggerWindowEvent('resize');\n        _this7._triggerEvent('toggle');\n        _this7._setMenuHoverState(false);\n      });\n    } else {\n      this._addClass('layout-no-transition');\n      if (collapsed) this._setMenuHoverState(false);\n\n      // Collapse / Expand\n      this._setCollapsed(collapsed);\n      setTimeout(function () {\n        _this7._removeClass('layout-no-transition');\n        _this7._triggerWindowEvent('resize');\n        _this7._triggerEvent('toggle');\n        _this7._setMenuHoverState(false);\n      }, 1);\n    }\n  },\n  // ---\n  // Toggle layout\n  toggleCollapsed: function toggleCollapsed() {\n    var animate = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : true;\n    this.setCollapsed(!this.isCollapsed(), animate);\n  },\n  // ---\n  // Set layout positioning\n  setPosition: function setPosition() {\n    var fixed = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : requiredParam('fixed');\n    var offcanvas = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : requiredParam('offcanvas');\n    this._removeClass('layout-menu-offcanvas layout-menu-fixed layout-menu-fixed-offcanvas');\n    if (!fixed && offcanvas) {\n      this._addClass('layout-menu-offcanvas');\n    } else if (fixed && !offcanvas) {\n      this._addClass('layout-menu-fixed');\n      this._redrawLayoutMenu();\n    } else if (fixed && offcanvas) {\n      this._addClass('layout-menu-fixed-offcanvas');\n      this._redrawLayoutMenu();\n    }\n    this.update();\n  },\n  // *******************************************************************************\n  // * Getters\n  getLayoutMenu: function getLayoutMenu() {\n    return document.querySelector('.layout-menu');\n  },\n  getMenu: function getMenu() {\n    var layoutMenu = this.getLayoutMenu();\n    if (!layoutMenu) return null;\n    return !this._hasClass('menu', layoutMenu) ? layoutMenu.querySelector('.menu') : layoutMenu;\n  },\n  getLayoutNavbar: function getLayoutNavbar() {\n    return document.querySelector('.layout-navbar');\n  },\n  getLayoutFooter: function getLayoutFooter() {\n    return document.querySelector('.content-footer');\n  },\n  // *******************************************************************************\n  // * Update\n  update: function update() {\n    if (this.getLayoutNavbar() && (!this.isSmallScreen() && this.isLayoutNavbarFull() && this.isFixed() || this.isNavbarFixed()) || this.getLayoutFooter() && this.isFooterFixed()) {\n      this._updateInlineStyle(this._getNavbarHeight(), this._getFooterHeight());\n    }\n    this._bindMenuMouseEvents();\n  },\n  setAutoUpdate: function setAutoUpdate() {\n    var _this8 = this;\n    var enable = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : requiredParam('enable');\n    if (enable && !this._autoUpdate) {\n      this.on('resize.Helpers:autoUpdate', function () {\n        return _this8.update();\n      });\n      this._autoUpdate = true;\n    } else if (!enable && this._autoUpdate) {\n      this.off('resize.Helpers:autoUpdate');\n      this._autoUpdate = false;\n    }\n  },\n  // *******************************************************************************\n  // * Tests\n  isRtl: function isRtl() {\n    return document.querySelector('body').getAttribute('dir') === 'rtl' || document.querySelector('html').getAttribute('dir') === 'rtl';\n  },\n  isMobileDevice: function isMobileDevice() {\n    return typeof window.orientation !== 'undefined' || navigator.userAgent.indexOf('IEMobile') !== -1;\n  },\n  isSmallScreen: function isSmallScreen() {\n    return (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) < this.LAYOUT_BREAKPOINT;\n  },\n  isLayoutNavbarFull: function isLayoutNavbarFull() {\n    return !!document.querySelector('.layout-wrapper.layout-navbar-full');\n  },\n  isCollapsed: function isCollapsed() {\n    if (this.isSmallScreen()) {\n      return !this._hasClass('layout-menu-expanded');\n    }\n    return this._hasClass('layout-menu-collapsed');\n  },\n  isFixed: function isFixed() {\n    return this._hasClass('layout-menu-fixed layout-menu-fixed-offcanvas');\n  },\n  isNavbarFixed: function isNavbarFixed() {\n    return this._hasClass('layout-navbar-fixed') || !this.isSmallScreen() && this.isFixed() && this.isLayoutNavbarFull();\n  },\n  isFooterFixed: function isFooterFixed() {\n    return this._hasClass('layout-footer-fixed');\n  },\n  isLightStyle: function isLightStyle() {\n    return document.documentElement.classList.contains('light-style');\n  },\n  // *******************************************************************************\n  // * Events\n  on: function on() {\n    var event = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : requiredParam('event');\n    var callback = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : requiredParam('callback');\n    var _event$split = event.split('.'),\n      _event$split2 = _slicedToArray(_event$split, 1),\n      _event = _event$split2[0];\n    var _event$split3 = event.split('.'),\n      _event$split4 = _toArray(_event$split3),\n      namespace = _event$split4.slice(1);\n    // let [_event, ...namespace] = event.split('.')\n    namespace = namespace.join('.') || null;\n    this._listeners.push({\n      event: _event,\n      namespace: namespace,\n      callback: callback\n    });\n  },\n  off: function off() {\n    var _this9 = this;\n    var event = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : requiredParam('event');\n    var _event$split5 = event.split('.'),\n      _event$split6 = _slicedToArray(_event$split5, 1),\n      _event = _event$split6[0];\n    var _event$split7 = event.split('.'),\n      _event$split8 = _toArray(_event$split7),\n      namespace = _event$split8.slice(1);\n    namespace = namespace.join('.') || null;\n    this._listeners.filter(function (listener) {\n      return listener.event === _event && listener.namespace === namespace;\n    }).forEach(function (listener) {\n      return _this9._listeners.splice(_this9._listeners.indexOf(listener), 1);\n    });\n  },\n  // *******************************************************************************\n  // * Life cycle\n  init: function init() {\n    var _this10 = this;\n    if (this._initialized) return;\n    this._initialized = true;\n\n    // Initialize `style` element\n    this._updateInlineStyle(0);\n\n    // Bind window resize event\n    this._bindWindowResizeEvent();\n\n    // Bind init event\n    this.off('init._Helpers');\n    this.on('init._Helpers', function () {\n      _this10.off('resize._Helpers:redrawMenu');\n      _this10.on('resize._Helpers:redrawMenu', function () {\n        // eslint-disable-next-line no-unused-expressions\n        _this10.isSmallScreen() && !_this10.isCollapsed() && _this10._redrawLayoutMenu();\n      });\n\n      // Force repaint in IE 10\n      if (typeof document.documentMode === 'number' && document.documentMode < 11) {\n        _this10.off('resize._Helpers:ie10RepaintBody');\n        _this10.on('resize._Helpers:ie10RepaintBody', function () {\n          if (_this10.isFixed()) return;\n          var scrollTop = document.documentElement.scrollTop;\n          document.body.style.display = 'none';\n          // document.body.offsetHeight\n          document.body.style.display = 'block';\n          document.documentElement.scrollTop = scrollTop;\n        });\n      }\n    });\n    this._triggerEvent('init');\n  },\n  destroy: function destroy() {\n    var _this11 = this;\n    if (!this._initialized) return;\n    this._initialized = false;\n    this._removeClass('layout-transitioning');\n    this._removeInlineStyle();\n    this._unbindLayoutAnimationEndEvent();\n    this._unbindWindowResizeEvent();\n    this._unbindMenuMouseEvents();\n    this.setAutoUpdate(false);\n    this.off('init._Helpers');\n\n    // Remove all listeners except `init`\n    this._listeners.filter(function (listener) {\n      return listener.event !== 'init';\n    }).forEach(function (listener) {\n      return _this11._listeners.splice(_this11._listeners.indexOf(listener), 1);\n    });\n  },\n  // ---\n  // Init Password Toggle\n  initPasswordToggle: function initPasswordToggle() {\n    var toggler = document.querySelectorAll('.form-password-toggle i');\n    if (typeof toggler !== 'undefined' && toggler !== null) {\n      toggler.forEach(function (el) {\n        el.addEventListener('click', function (e) {\n          e.preventDefault();\n          var formPasswordToggle = el.closest('.form-password-toggle');\n          var formPasswordToggleIcon = formPasswordToggle.querySelector('i');\n          var formPasswordToggleInput = formPasswordToggle.querySelector('input');\n          if (formPasswordToggleInput.getAttribute('type') === 'text') {\n            formPasswordToggleInput.setAttribute('type', 'password');\n            formPasswordToggleIcon.classList.replace('bx-show', 'bx-hide');\n          } else if (formPasswordToggleInput.getAttribute('type') === 'password') {\n            formPasswordToggleInput.setAttribute('type', 'text');\n            formPasswordToggleIcon.classList.replace('bx-hide', 'bx-show');\n          }\n        });\n      });\n    }\n  },\n  // ---\n  // Init Speech To Text\n  initSpeechToText: function initSpeechToText() {\n    var SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;\n    var speechToText = document.querySelectorAll('.speech-to-text');\n    if (SpeechRecognition !== undefined && SpeechRecognition !== null) {\n      if (typeof speechToText !== 'undefined' && speechToText !== null) {\n        var recognition = new SpeechRecognition();\n        var toggler = document.querySelectorAll('.speech-to-text i');\n        toggler.forEach(function (el) {\n          var listening = false;\n          el.addEventListener('click', function () {\n            el.closest('.input-group').querySelector('.form-control').focus();\n            recognition.onspeechstart = function () {\n              listening = true;\n            };\n            if (listening === false) {\n              recognition.start();\n            }\n            recognition.onerror = function () {\n              listening = false;\n            };\n            recognition.onresult = function (event) {\n              el.closest('.input-group').querySelector('.form-control').value = event.results[0][0].transcript;\n            };\n            recognition.onspeechend = function () {\n              listening = false;\n              recognition.stop();\n            };\n          });\n        });\n      }\n    }\n  },\n  // Ajax Call Promise\n  ajaxCall: function ajaxCall(url) {\n    return new Promise(function (resolve, reject) {\n      var req = new XMLHttpRequest();\n      req.open('GET', url);\n      req.onload = function () {\n        return req.status === 200 ? resolve(req.response) : reject(Error(req.statusText));\n      };\n      req.onerror = function (e) {\n        return reject(Error(\"Network Error: \".concat(e)));\n      };\n      req.send();\n    });\n  },\n  // ---\n  // SidebarToggle (Used in Apps)\n  initSidebarToggle: function initSidebarToggle() {\n    var sidebarToggler = document.querySelectorAll('[data-bs-toggle=\"sidebar\"]');\n    sidebarToggler.forEach(function (el) {\n      el.addEventListener('click', function () {\n        var target = el.getAttribute('data-target');\n        var overlay = el.getAttribute('data-overlay');\n        var appOverlay = document.querySelectorAll('.app-overlay');\n        var targetEl = document.querySelectorAll(target);\n        targetEl.forEach(function (tel) {\n          tel.classList.toggle('show');\n          if (typeof overlay !== 'undefined' && overlay !== null && overlay !== false && typeof appOverlay !== 'undefined') {\n            if (tel.classList.contains('show')) {\n              appOverlay[0].classList.add('show');\n            } else {\n              appOverlay[0].classList.remove('show');\n            }\n            appOverlay[0].addEventListener('click', function (e) {\n              e.currentTarget.classList.remove('show');\n              tel.classList.remove('show');\n            });\n          }\n        });\n      });\n    });\n  }\n};\n\n// *******************************************************************************\n// * Initialization\n\nif (typeof window !== 'undefined') {\n  Helpers.init();\n  if (Helpers.isMobileDevice() && window.chrome) {\n    document.documentElement.classList.add('layout-menu-100vh');\n  }\n\n  // Update layout after page load\n  if (document.readyState === 'complete') Helpers.update();else document.addEventListener('DOMContentLoaded', function onContentLoaded() {\n    Helpers.update();\n    document.removeEventListener('DOMContentLoaded', onContentLoaded);\n  });\n}\n\n// ---\nwindow.Helpers = Helpers;\n\n\n//# sourceURL=webpack://sneat-bootstrap-html-admin-template-free/./js/helpers.js?");

                /***/
            })

            /******/
        });
        /************************************************************************/
        /******/ 	// The require scope
        /******/
        var __webpack_require__ = {};
        /******/
        /************************************************************************/
        /******/ 	/* webpack/runtime/define property getters */
        /******/
        !function () {
            /******/ 		// define getter functions for harmony exports
            /******/
            __webpack_require__.d = function (exports, definition) {
                /******/
                for (var key in definition) {
                    /******/
                    if (__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
                        /******/
                        Object.defineProperty(exports, key, {enumerable: true, get: definition[key]});
                        /******/
                    }
                    /******/
                }
                /******/
            };
            /******/
        }();
        /******/
        /******/ 	/* webpack/runtime/hasOwnProperty shorthand */
        /******/
        !function () {
            /******/
            __webpack_require__.o = function (obj, prop) {
                return Object.prototype.hasOwnProperty.call(obj, prop);
            }
            /******/
        }();
        /******/
        /******/ 	/* webpack/runtime/make namespace object */
        /******/
        !function () {
            /******/ 		// define __esModule on exports
            /******/
            __webpack_require__.r = function (exports) {
                /******/
                if (typeof Symbol !== 'undefined' && Symbol.toStringTag) {
                    /******/
                    Object.defineProperty(exports, Symbol.toStringTag, {value: 'Module'});
                    /******/
                }
                /******/
                Object.defineProperty(exports, '__esModule', {value: true});
                /******/
            };
            /******/
        }();
        /******/
        /************************************************************************/
        /******/
        /******/ 	// startup
        /******/ 	// Load entry module and return exports
        /******/ 	// This entry module can't be inlined because the eval devtool is used.
        /******/
        var __webpack_exports__ = {};
        /******/
        __webpack_modules__["./js/helpers.js"](0, __webpack_exports__, __webpack_require__);
        /******/
        /******/
        return __webpack_exports__;
        /******/
    })()
        ;
});