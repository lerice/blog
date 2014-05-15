class Article < ActiveRecord::Base
  attr_accessible :text, :title
  
  has_many :comments, dependent: :destroy
  
  validates :title, presence: true,
					length: { minimum: 5 }
					
  def self.search(search)
    if search
	  find(:all, :conditions => ['LOWER (title) LIKE ? OR LOWER (text) LIKE ?', "%#{search.downcase}%", "%#{search.downcase}%"])
	else
	  find(:all)
	end
  end
  
  def to_param
    "#{id}-#{title.parameterize}"
  end
end
