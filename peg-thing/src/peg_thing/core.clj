(ns peg-thing.core
  (:gen-class))

(declare successful-move prompt-move game-over query-rows)


(defn tri*
  ([] (tri* 0 1))
  ([sum n]
   (let [newsum (+ sum n)]
     (cons newsum (lazy-seq (tri* newsum (inc n)))))))

(def tri (tri*))

(take 5 tri)

(defn triangular?
  [n]
  (= n (last (take-while #(<= n) tri))))

(triangular? 1)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
