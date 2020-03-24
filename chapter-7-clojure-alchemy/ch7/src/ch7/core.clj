(ns ch7.core
  (:gen-class))


;; implementing pair abstraction in terms of procedures
;; procedure as data
;; cons returns a fn instead of a data structure
(defn cons' [x y]
  (fn [pick]
    (if (= pick 1) x
        (if  (= pick 2) y))))

(defn car' [x] (x 1))

(defn cdr' [x] (x 2))

;; pair abstraction
(def x (cons' 2 3))
(= (car' x) 2)
(= (cdr' x) 3)
