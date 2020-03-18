(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int    
  [str]    
  (Integer. str))

(def conversions 
  {:name identity 
   :glitter-index str->int})

(defn convert    
  [vamp-key value]    
  ((get conversions vamp-key) value))

(defn parse  
  "Convert a CSV into rows of columns"  
  [string]  
  (map 
   #(clojure.string/split % #",")      
   (clojure.string/split string #"\n")))

(defn convert-row
  [row]
  (map convert vamp-keys row))

(def db (parse (slurp filename)))
(map convert-row db)

(defn mapify
  "Returns a seq of maps like {:name '' :glitter-index n}"
  [rows]
  (map (fn kv [[name index]] 
         {:name name :glitter-index index})
        (map convert-row rows)))


(defn textbookmapify
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [key val]]
                   (assoc row-map key (convert key val)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))


(def ks [:a :b])
(def vs [1 2])
(def kvs (map vector ks vs))
(map (fn [[k v]] (assoc {} k v))
     kvs)

(into {} db)

(convert-row ["Ed" "19"])
(= (mapify db)
   (textbookmapify db))

(defn glitter-filter
  [minimum-glitter records]
  (map :name
       (filter #(>= (:glitter-index %) minimum-glitter) 
               records)))

(glitter-filter 3 (textbookmapify db))

(defn append [suspects suspect]
  (if (validate validations suspect)
      (conj suspects suspect)
      (do 
        (println "Invalid suspect!") 
        suspects)))

(append (mapify  (parse (slurp filename))) 
        {:name "Shinto" :glitter-index "7"})

(def validations
  {:name #(contains? % :name)
   :glitter-index #(contains? % :glitter-index)})

(defn validate [validations suspect]
  (every? (fn [[key validation]]
              (validation suspect))  validations))

(def newsuspect {:name "Sh" :glitter-index "10"})

(validate validations newsuspect)
