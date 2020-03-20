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
  (= n (last (take-while #(<= % n) tri))))

(defn row-tri
  [n]
  (last (take n tri)))

(row-tri 4)
(triangular? 3)

(defn row-num
  [pos]
  ((comp inc count) (take-while #(> pos %) tri)))

(take-while #(> 5 %) tri)

(row-num 11)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
