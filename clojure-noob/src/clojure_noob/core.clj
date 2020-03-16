(ns clojure-noob.core
  (:gen-class))

(defn -main
 
  [& args]
  (println "I am a little teapot!"))

(println "hello world")
(* 2 5)

(defn train
  []
  (println "choo choo!"))

(* 1 2 3)

(if false
  (do (println "Success!")
      "By Zeus!")
  (do (println "Failure!")
      "By Aquaman!"))

(when true
  (println "Success!")
  "Abracadabra!")

(defn error-message
  [severity]
  (str "Oh god! It's a disaster! We're "
       (if (= severity :mild)
         "mildly inconvenienced!"
         "dooomed!")))


(error-message :mild)

(defn sq [x]
  (* x x))

(defn sos [x y]
  (+ (sq x) (sq y)))

(sos 3 4)

(defn inc-maker
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)

(def dalmation-list ["pongo", "Perdita", "puppy1", "puppy2"])
(let [[pongo & dalmations] dalmation-list]
  [pongo dalmations])
