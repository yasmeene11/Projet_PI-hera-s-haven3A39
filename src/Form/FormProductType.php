<?php

namespace App\Form;

use App\Entity\Product;
use Symfony\Component\DependencyInjection\ContainerInterface;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\DataTransformerInterface;
use Symfony\Component\Form\Exception\TransformationFailedException;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FormProductType extends AbstractType
{
    private $container;

    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;
    }

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Product_Name', TextType::class, [
                'label' => 'Product_Name',
                'attr' => [
                    'placeholder' => 'Enter product name',
                ],
                'required' => false,
            ])
            ->add('Product_Quantity', TextType::class, [
                'label' => 'Product_Quantity',
                'attr' => [
                    'placeholder' => 'Enter product quantity',
                ],
                'required' => false,
            ])
            ->add('Product_Label', TextType::class, [
                'label' => 'Product_Label',
                'attr' => [
                    'placeholder' => 'Enter product label',
                ],
                'required' => false,
            ])
            ->add('Expiration_Date', DateType::class, [
                'widget' => 'single_text',
            ])
            ->add('Category_Key')
            ->add('Product_Image', FileType::class, [
                'label' => 'Product_Image',
                'required' => false,
            ]);

        $builder->get('Product_Image')->addModelTransformer(new class ($this->container) implements DataTransformerInterface {
            private $container;

            public function __construct(ContainerInterface $container)
            {
                $this->container = $container;
            }

            public function transform($value)
            {
                if ($value instanceof \Symfony\Component\HttpFoundation\File\File) {
                    return $value->getFilename();
                }

                return null;
            }

            public function reverseTransform($value)
            {
                if ($value instanceof \Symfony\Component\HttpFoundation\File\UploadedFile) {
                    return $value;
                }

                if (is_string($value)) {
                    $uploadDirectory = $this->container->getParameter('kernel.project_dir') . '/public/Product_images/';
                    $filePath = $uploadDirectory . $value;

                    if (file_exists($filePath)) {
                        return new \Symfony\Component\HttpFoundation\File\File($filePath);
                    }

                    throw new TransformationFailedException("File not found: $value");
                }

                return null;
            }
        });
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Product::class,
        ]);
    }
}