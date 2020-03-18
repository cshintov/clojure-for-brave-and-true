(ns ch5.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn two-comp
  [f g]
  (fn [& args ] 
    (f (apply g args))))

((two-comp inc *) 2 3)

(defn mycomp
  [& functions]
  (do
    (println functions)
    (fn [& args] 
      (apply (reduce two-comp
                     identity
                     functions) args))))
;;  (apply comp functions args))

((mycomp #(+ % 2) inc * ) 2 3)

(defn sleepy-id [x]
  (Thread/sleep 1000)
  x)

(time (sleepy-id "Me"))

(def memoized-sleepy-id (memoize sleepy-id))

(time (memoized-sleepy-id "Me"))
(time (memoized-sleepy-id "Me"))

(defn fib [n]
  (if (< n 2)
    n
    (+' (fib (- n 1)) (fib (- n 2))
     )))

(def fib (memoize fib))
